scp -r www itmo:~/web/lab01
#scp -r build/libs/*.jar itmo:~/httpd-root/fcgi-bin
scp -r build/libs/*.jar itmo:~/httpd-root/fcgi-bin
scp httpd.conf itmo:~/httpd-root/conf