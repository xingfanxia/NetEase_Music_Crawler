#!/usr/bin/python

import paramiko
import threading
import time

ip = '162.243.157.104'
user = 'root'
password = 'dsatcarleton'
jar = 'netEase-Crawler-0.1.jar'
home='/root/netEase-Crawler'
current=home+"/current"
releases=home+"/releases"

def execute_cmds(ip, user, passwd, cmd):
    try:
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(ip,22,user,passwd,timeout=5)
        for m in cmd:
            print m
            stdin, stdout, stderr = ssh.exec_command(m)
#           stdin.write("Y")
            out = stdout.readlines()
            for o in out:
                print o,
        print '%s\tOK\n'%(ip)
        ssh.close()
    except :
        print '%s\tError\n'%(ip)


if __name__=='__main__':
    print 'Start deploying %s to server %s'%(jar, ip)

    now = time.strftime("%Y%m%d%H%M%S")
    cmd = [
        'echo Stop spring_blog service... && service spring_blog stop',
        'echo Flush all redis cache data... && redis-cli -r 1 flushall',
        'echo Stop redis server... && service redis_6379 stop',
        'echo Use new jar... ' + \
        ' && mv ' + current + '/' + jar + ' ' + releases + '/' + now + '_' + jar ,
        'mv ' + home + '/' + jar + ' ' + current + '/' + jar,
        'echo Stop redis... && service redis_6379 start',
        'echo Start spring_blog service... && service spring_blog start ' + \
        ' && echo All done.'
    ]
    a=threading.Thread(target=execute_cmds, args=(ip,user,password,cmd))
    a.start()