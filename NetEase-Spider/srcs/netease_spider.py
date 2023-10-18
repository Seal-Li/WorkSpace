import re
import time
import random
import requests
from bs4 import BeautifulSoup
import mysql.connector
import  config
import concurrent.futures
from tqdm import tqdm

def get_soup(song_id, header, timeout):
    # 构造请求URL
    url = f'https://music.163.com/song?id={song_id}'

    # 构造请求头
    headers = {'User-Agent': header}
    try:
        # 发送GET请求
        response = requests.get(url, headers=headers, timeout=timeout)
    except requests.exceptions.Timeout:
        print("请求超时，请检查网络连接或增加超时时间")
        return None
    except requests.exceptions.RequestException as e:
        print(f"请求发生异常：{e}")
        return None

    return BeautifulSoup(response.text, 'html.parser')

def get_song_title(soup):
    if not soup:
        return None
    # 获取歌曲名称
    song_name_tag = soup.find('meta', property='og:title')
    song_name = song_name_tag['content'] if song_name_tag else 'Unknown'

    # 获取歌手名字
    singer_name_tag = soup.find('meta', property='og:music:artist')
    singer_name = singer_name_tag['content'] if singer_name_tag else 'Unknown'
    return song_name, singer_name

def get_lyric(song_id, header, timeout):
    # sourcery skip: remove-unnecessary-else, swap-if-else-branches
    url = f'http://music.163.com/api/song/lyric?id={song_id}&lv=1&kv=1&tv=-1'
    headers = {'User-Agent': header}

    try:
        # 发送GET请求
        response = requests.get(url, headers=headers, timeout=timeout)
    except requests.exceptions.RequestException as e:
        print(f"请求发生异常：{e}")
        return None, None
    
    # 解析JSON响应
    data = response.json()

    # 检查是否成功获取歌词
    if 'lrc' in data and 'lyric' in data['lrc']:
        lyrics = data['lrc']['lyric']
        lyrics_without_time = re.sub(r'\[\d+:\d+\.\d+\]', '', lyrics)
        lines = lyrics_without_time.split('\n')
        lines_with_colon = {line.split(':')[0].strip(): line.split(':', 1)[1].strip() for line in lines if ':' in line}
        return lyrics_without_time, lines_with_colon
    else:
        return 'Lyrics not found', []

def handle_titles(lines_with_colon, title_list):
    if len(lines_with_colon) == 0:
        return {}
    else:
        return {key: value for key, value in lines_with_colon.items() if key in title_list}




def data_insert(data_to_insert, args):
    try:
        conn = db_pool.get_connection()
        cursor = conn.cursor()
        insert_query = f'''
        INSERT INTO {args.table_name} (song_id, song_name, singer, title_map, lyric)
        VALUES (%s, %s, %s, %s, %s)
        '''
        cursor.executemany(insert_query, data_to_insert)
        conn.commit()
    except mysql.connector.Error as err:
        print("Error:", err)
    finally:
        cursor.close()
        conn.close()
    return None


def myfunc(song_ids, args):  # sourcery skip: use-named-expression
    start_id = song_ids[0]
    end_id = song_ids[1]
    batch_data_to_insert = []
    insert_condition = min(args.insert_every, args.interval)
    for song_id in tqdm(range(start_id, end_id)):
        header = random.choice(args.agents)
        soup = get_soup(song_id, header, args.timeout)
        if soup:
            song_name, singer_name = get_song_title(soup)
            if ('网易云音乐' in song_name) and singer_name == 'Unknown':
                continue
            lyrics, lines_with_colon = get_lyric(song_id, header, args.timeout)
            title_dict = handle_titles(lines_with_colon, args.title_list)
        else:
            print(f"{song_id} Request Error , the proxy {header} has been forbidden, please delete it !")
            continue
        values = (str(song_id), song_name, singer_name, str(title_dict), lyrics)
        batch_data_to_insert.append(values)
        time.sleep(random.uniform(0.5, 2))
        if (song_id - start_id + 1) % insert_condition == 0:
            data_insert(batch_data_to_insert, args)
            batch_data_to_insert = []
    return None


# sourcery skip: comprehension-to-generator
if __name__ == "__main__":
    args = config.argparser()
    global db_pool
    db_pool = mysql.connector.pooling.MySQLConnectionPool(
        pool_name="my_pool",
        pool_size=args.num_threads,
        host=args.host,
        user=args.user,
        password=args.password,
        database=args.database
    )
    song_ids = [
        [args.start_id + i * (args.interval), args.start_id + (i + 1) * (args.interval)]
        for i in range(args.num_threads)
    ]
    with concurrent.futures.ThreadPoolExecutor(max_workers=args.num_threads) as executor:
        # 提交任务给线程池，每个任务传递不同的参数
        tasks = [executor.submit(myfunc, ids, args) for ids in song_ids]
        # 等待所有任务完成
        concurrent.futures.wait(tasks)
        count = 0
        for future in concurrent.futures.as_completed(tasks):
            if future.done():
                count += 1
            else:
                print("Task is still running.")
        print(f"finished task: {count}")
        with open(args.data_path, "w+", encoding="utf-8") as f:
            f.write(f"{args.start_id + args.num_threads * args.interval}")
        # print("\n\n\ntask is over!")