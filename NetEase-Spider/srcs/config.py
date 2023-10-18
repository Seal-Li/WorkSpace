import argparse
from agents import agents
import numpy as np

def argparser():
    # 创建 ArgumentParser 对象
    parser = argparse.ArgumentParser()
    # 添加命令行参数
    parser.add_argument("--agents", 
                        type=list, 
                        default=agents, 
                        help="Aproxy Pool")
    parser.add_argument("--timeout", 
                        type=tuple,
                        default=(30, 30),
                        help="Get requests max reponse time")
    parser.add_argument("--host", 
                        type=str,
                        default='localhost')
    parser.add_argument("--user", 
                        type=str,
                        default='root')
    parser.add_argument("--password", 
                        type=str,
                        default='980523')
    parser.add_argument("--database",
                        type=str,
                        default='netease')
    parser.add_argument("--table_name",
                        type=str,
                        default='netease_music_titles_all')
    parser.add_argument("--title_list",
                        type=list,
                        default = ['作词', '作曲', '编曲', '制作人', '录音', '吉他', '原唱'])
    parser.add_argument("--start_id", 
                        type=int,
                        default=int(np.genfromtxt('data/record.txt')),
                        help="Song's id that from it start to execte this task")
    parser.add_argument("--interval", 
                        type=int,
                        default=1500,
                        help="The number of song that every thread to operate")
    parser.add_argument("--num_threads", 
                        type=int,
                        default=20,
                        help="Max number of threads")
    parser.add_argument("--insert_every",
                        type=int,
                        default=100,
                        help="Number of data insert to mysql database everytime")
    parser.add_argument("--data_path",
                        type=str,
                        default='data/record.txt',
                        help="Number of data insert to mysql database everytime")
    return parser.parse_args()