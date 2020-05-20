1、启动redis服务：
    打开cmd命令，进入c盘根目录下的redis文件夹：cd c:\Redis
    启动命令：redis-server.exe redis.windows.conf

    查看redis命令：
    打开cmd窗口，进入c盘根目录下的redis文件夹：cd c:\Redis
    执行命令：redis-cli.exe -h 127.0.0.1 -p 6379
    查看命令：keys *

2、启动nginx服务
    打开cmd窗口，进入c盘根目录下的nginx文件夹：cd c:\nginx-1.17.10
    启动命令：nginx start
    重启命令：nginx -s reload
    测试：打开浏览器访问localhost：80

