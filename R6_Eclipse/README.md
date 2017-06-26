# SpdataR6<br>
###文件说明<br>
	“r6_as”文件夹中存放了r6的android stuido版开发源码，包含了lib文件。
    “r6_eclipse”文件夹内分别存放了r6demo的源码以及其需要导入的lib文件“r6lib”。安装包文件“r6demo.apk”是r6的demo文件。
    此文件已写有API文档，在线API文档链接：https://www.showdoc.cc/11377?page_id=102430
###功能介绍<br>
	本程序适用于思必拓手持终端设备中，装有高频读卡模块的设备。能识别读取的非接卡种类有：CPUA、CPUB、ISO15693、Mifare、Ultralight等。
###使用说明<br>
	在任一支持高频读卡的思必拓手持终端设备中，安装“r6demo.apk”文件后，会生成一个名为“R6”的程序图标。
	点击进入后，在界面上方有五种卡片的对应按钮。其中，选择CPU卡后，继续在本界面进行操作，选择其他卡类则会跳转到其相应的界面。
###开发说明<br>
	使用Android Studio的开发人员可直接在开发工具中打开相应的源码，参考源码进行开发。
	不使用源码则需要在build.gradle文件中添加：compile‘com.speedata:r6lib:1.4’
    使用Eclipse的开发人员，需要把“r6_eclipse”文件夹中的两个源码都导入开发工具，其中“r6lib”作为library，被r6程序源码调用。
###API文档<br>
**·导入依赖库** <br>
---
**AndroidStudio** build.gradle中的dependencies中添加
compile ‘com.speedata：r6lib:1.4’
示例：
```
 dependencies {
 compile 'com.android.support:appcompat-v7:25.0.1'
 compile 'com.speedata:r6lib:1.4'
}
```
**Eclipse** 导入libs库，资料包中的“r6_eclipse”文件夹内的r6lib是其依赖项目
同时java build path中的Order and Export需要勾选此libs。
如运行报错class not found 则需手动在project.properties中填写依赖关系 如：
```
 android.library.reference.1=../r6lib
```

**·R6Manager** <br>
-----------
#### CPU卡：

|函数原型|public static IR6Manager getInstance(CardType cardType)|
|:----    |:---|
|功能描述 |判断模块实例化，选择卡片类型|
|参数说明 |无  |
|返回类型 |实例化对象|

 **示例**

```
 //选择卡类型为cpuA卡。同理cpuB卡为：CPUB
 private IR6Manager mIR6Manager;//定义对象
 mIR6Manager = R6Manager.getInstance(CPUA);//选择卡类型：CPUA
```


#### ISO15693卡：

|函数原型|public static ISO15693Manager get15693Instance(CardType cardType)|
|:----    |:---|
|功能描述 |判断模块实例化，选择卡片类型15693|
|参数说明 |无  |
|返回类型 |实例化对象|

 **示例**

```
 //选择卡类型为15693卡。
 private ISO15693Manager dev;//定义对象
 dev = R6Manager.get15693Instance(ISO15693);//选择卡类型：15693
```

#### Mifare卡:

|函数原型|public static IMifareManager getMifareInstance(CardType cardType)|
|:----    |:---|
|功能描述 |判断模块实例化，选择卡片类型Mifare|
|参数说明 |无  |
|返回类型 |实例化对象|

 **示例**

```
 //选择卡类型为Mifare卡。
 private IMifareManager dev;//定义对象
 dev = R6Manager.getMifareInstance(MIFARE);//选择卡类型：15693
```

#### Ultralight卡：

|函数原型|public static IUltralightManager getUltralightInstance(CardType cardType)|
|:----    |:---|
|功能描述 |判断模块实例化，选择卡片类型Ultralight|
|参数说明 |无  |
|返回类型 |实例化对象|

 **示例**

```
 //选择卡类型为Ultralight卡。
 private IUltralightManager dev;//定义对象
 dev = R6Manager.getUltralightInstance(ULTRALIGHT);//选择卡类型：Ultralight
```


 **·API接口** <br>
-----------
CPU卡
---
####1.设备上电<br>
**·InitDevice()**

|函数原型|public int InitDevice()|
|:----    |:---|
|功能描述 |设备上电。在上电后即可进行寻卡等操作，无需多次上电|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  mIR6Manager.InitDevice();
```


####2.设备下电<br>
**·ReleaseDevice()**

|函数原型|public void ReleaseDevice()|
|:----    |:---|
|功能描述 |设备下电|
|参数说明 |无  |
|返回类型 |无|

 **示例**

```
  mIR6Manager.ReleaseDevice();
```

####3.CPU卡寻卡<br>
**·SearchCard()**

|函数原型|public byte[ ] SearchCard()|
|:----    |:---|
|功能描述 |寻卡操作|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为卡片ID；失败返回null。|

 **示例**

```
//寻卡示例代码，返回的数据可以处理为String型便于查看
 byte[] sCard = mIR6Manager.SearchCard();
                String sCards = "";
                if (sCard !=null) {
                    for (byte i : sCard) {
                        sCards += String.format("%02X", i);
                    }
                }
```

####4.CPU卡读卡<br>
**·ReadCard()**

|函数原型|public byte[ ] ReadCard()|
|:----    |:---|
|功能描述 |读取CPU卡的数据|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null|

 **示例**

```
//读卡操作后，可转化为String型便于查看
 byte[] eCard = mIR6Manager.ReadCard();
                String eCards = "";
                if (eCard !=null) {
                    for (byte i : eCard) {
                        eCards += String.format("%02X", i);
                    }
                }
```

####5.移除卡片<br>
**·Deselect()**

|函数原型|public int Deselect()|
|:----    |:---|
|功能描述 |将卡片移除，使其不能被寻卡操作寻找到|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  mIR6Manager.Deselect();
```

####6.发送指令<br>
**·ExecCmdInput(String input)**

|函数原型|public byte[] ExecCmdInput(String input)|
|:----    |:---|
|功能描述 |发送标准指令对CPU卡操作|
|参数说明 |指令格式，如随机数指令：00 84 00 00 08|
|返回类型 |成功返回byte[ ]，内容为返回的数据；失败返回null。|

 **示例**

```
//判断非空输入后，发送指令。如果有返回的数据就转换成String型便于查看
  String cmd = etCmdInput.getText().toString();
                if ("".equals(cmd)){
                    Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] xs = mIR6Manager.ExecCmdInput(cmd);//发送指令

                if(xs == null)
                {
                    etShow.setText("exchange l4 failed");
                    setCmdFalse();
                    mIR6Manager.ReleaseDevice();
                }
                else
                {
                    Log.i(TAG, "cmd retured value begin");
                    String res = "";
                    for(byte i : xs)
                    {
                        res += String.format("%02x ", i);
                    }
                    etShow.append(res);
                    getLast();
                    tvTitle.setText("execute cmd ok");
                }
```


ISO15693卡
---
####1.设备上电<br>
**·InitDevice()**

|函数原型|public int InitDevice()|
|:----    |:---|
|功能描述 |设备上电|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  dev.InitDevice();
```

####2.设备下电<br>
**·ReleaseDevice()**

|函数原型|public void ReleaseDevice()|
|:----    |:---|
|功能描述 |设备下电|
|参数说明 |无  |
|返回类型 |无|

 **示例**

```
  dev.ReleaseDevice();
```

####3.非接卡寻卡<br>
**·SearchCard()**

|函数原型|public byte[ ] SearchCard()|
|:----    |:---|
|功能描述 |寻卡|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.SearchCard();
```

####4.非接卡读卡<br>
**·ReadCardInfo()**

|函数原型|public byte[ ] ReadCardInfo()|
|:----    |:---|
|功能描述 |读卡|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.ReadCardInfo();
```

####5.写DSFID<br>
**·WriteDSFID(int option, byte DSFID)**

|函数原型|public int WriteDSFID(int option, byte DSFID)|
|:----    |:---|
|功能描述 |写卡的DSFID|
|参数说明 |option：模式，DSFID：向DSFID写入的数据  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;

//（byte）0x22:将DSFID写成此数据

  dev.WriteDSFID(ISO15693_OPTION_OFF, (byte)0x22);
```

####6.锁定DSFID<br>
**·LockDSFID(int option)**

|函数原型|public int LockDSFID(int option)|
|:----    |:---|
|功能描述 |锁定卡的DSFID|
|参数说明 |option：模式    |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;

  dev.LockDSFID(ISO15693_OPTION_OFF);
```

####7.写AFI<br>
**·WriteAFI(int option, byte AFI)**

|函数原型|public int WriteAFI(int option, byte AFI)|
|:----    |:---|
|功能描述 |向AFI写入数据|
|参数说明 |option：模式，AFI：向AFI写入的数据  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;
//(byte)0x33:将AFI写为此数据

  dev.WriteAFI(ISO15693_OPTION_OFF, (byte)0x33);
```

####8.锁定AFI<br>
**·LockAFI(int option)**

|函数原型|public int LockAFI(int option)|
|:----    |:---|
|功能描述 |锁定卡的AFI|
|参数说明 |option：模式   |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;


  dev.LockAFI(ISO15693_OPTION_OFF);
```

####9.锁定block数据块<br>
**·LockBlock(int option, int block)**

|函数原型|public int LockBlock(int option, int block)|
|:----    |:---|
|功能描述 |锁定指定的block数据块|
|参数说明 |option：模式，block：要锁定的数据块  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;
//block：要锁定的数据块的编号


  dev.LockBlock(ISO15693_OPTION_OFF, block);
```


####10.读一个block数据块<br>
**·ReadSingleBlock(int option, int block)**

|函数原型|public byte[ ] ReadSingleBlock(int option, int block)|
|:----    |:---|
|功能描述 |读一个数据块的数据|
|参数说明 |option：模式，block：要读取的数据块 |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.ReadSingleBlock(ISO15693_OPTION_OFF, block);
```

####11.写一个block数据块<br>
**·WriteSingleBlock(int option, int block, byte[ ] data)**

|函数原型|public int WriteSingleBlock(int option, int block, byte[ ] data)|
|:----    |:---|
|功能描述 |向一个数据块写入数据|
|参数说明 |option：模式，block： 要写入的数据块，data： 要写入的数据  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//参数option：public static final int ISO15693_OPTION_OFF = 0;
	//public static final int ISO15693_OPTION_ON = 1;

  dev.WriteSingleBlock(ISO15693_OPTION_OFF, block, data);
```

####12.读多个blocks数据块<br>
**·ReadMultipleBlocks(int option, int firstblock, int block_nr)**

|函数原型|public byte[ ] ReadMultipleBlocks(int option, int firstblock, int block_nr)|
|:----    |:---|
|功能描述 |读取多个数据块的数据|
|参数说明 |option:模式，firstblock：起始数据块，block_nr：终止数据块|
|返回类型 |成功返回byte[ ]，内容为返回的数据；失败返回null。|

 **示例**

```
 ReadMultipleBlocks(ISO15693_OPTION_OFF, block, 16);
```

####13.写多个blocks数据块<br>
**·WriteMultipleBlocks(int option, int firstblock, int block_nr, byte[ ] data)**

|函数原型|public int WriteMultipleBlocks(int option, int firstblock, int block_nr, byte[ ] data)|
|:----    |:---|
|功能描述 |对多个数据块写入数据|
|参数说明 |option:模式，firstblock：起始写入块，block_nr：终止写入块  data：数据|
|返回类型 |成功返回byte[ ]，内容为返回的数据；失败返回null。|

 **示例**

```
dev.WriteMultipleBlocks(ISO15693_OPTION_OFF, block, 3, data);

```

####14.获取多个blocks数据块的安全状态<br>
**·GetMultipleBlockSecurityStatus(int firstblock, int nr_block)**

|函数原型|public byte[] GetMultipleBlockSecurityStatus(int firstblock, int nr_block)|
|:----    |:---|
|功能描述 |获取多个数据块的安全状态|
|参数说明 |firstblock：起始数据块， nr_block：截止数据块 |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
   //得到状态后可以处理结果便于查看
            res = dev.GetMultipleBlockSecurityStatus(block, 3);
            if(res == null)
            {
                main_info.append("Get multi blocks security status failed, maybe card don't support\n");
                return;
            }
            main_info.append("Get multi blocks security status ok, result is:\n");
            vblock = block;
            for(byte s : res)
            {
                main_info.append(String.format("block %d:	", vblock++) + (s == 0 ? "unlocked" : "locked") + "\n");
            }
```

Mifare卡
---
####1.设备上电<br>
**·InitDev()**

|函数原型|public int InitDev()|
|:----    |:---|
|功能描述 |设备上电|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  dev.InitDev();
```

####2.设备下电<br>
 **·ReleaseDev()**

|函数原型| public void ReleaseDev()|
|:----    |:---|
|功能描述 |设备下电|
|参数说明 |无  |
|返回类型 |无|

 **示例**

```
  dev.ReleaseDev();
```

####3.非接卡寻卡<br>
**·SearchCard()**

|函数原型|public byte[ ] SearchCard()|
|:----    |:---|
|功能描述 |寻卡|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.SearchCard();
```

####4.非接卡寻卡<br>
**·HaltCard()**

|函数原型|public int HaltCard()|
|:----    |:---|
|功能描述 |移除卡片，使其不能被寻找到|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  dev.HaltCard();
```

####5.确认卡片是否可读写<br>
**·AuthenticationCardByKey(int type, byte[ ] cid, int block, byte[ ] key)**

|函数原型|public int AuthenticationCardByKey(int type, byte[ ] cid, int block, byte[ ] key)|
|:----    |:---|
|功能描述 |确认卡片是否可读写|
|参数说明 |type：卡片类型，cid：卡片ID，block：数据块，key：秘钥 |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
//public static final int AUTH_TYPEA = 0xA;
	//public static final int AUTH_TYPEB = 0xB;

  dev.AuthenticationCardByKey(AUTH_TYPEA, ID, block, key);
```

####6.写一个block数据块<br>
**·WriteBlock(int block, byte[ ] data)**

|函数原型|public int WriteBlock(int block, byte[ ] data)|
|:----    |:---|
|功能描述 |对一个数据块写入数据|
|参数说明 |block：数据块，data：要写入的数据  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```

  dev.WriteBlock(block, data);
```

####7.读一个block数据块<br>
**· ReadBlock(int block)**

|函数原型|public byte[ ] ReadBlock(int block)|
|:----    |:---|
|功能描述 |读一个数据块|
|参数说明 |block：数据块  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.ReadBlock(block);
```

####8.减少一个数据块的value值<br>
**·DecrementBlockValue(int block, int value)**

|函数原型|public int DecrementBlockValue(int block, int value)|
|:----    |:---|
|功能描述 |减少一个数据块的value值|
|参数说明 |  block:数据块，value：降低block的value值  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```

  dev.DecrementBlockValue(block, 55);
```

####9.增加一个数据块的value值<br>
**·IncrementBlockValue(int block, int value)**

|函数原型| public int IncrementBlockValue(int block, int value)|
|:----    |:---|
|功能描述 |增加一个数据块的value值|
|参数说明 |block：数据块，value：此block块的提升value值  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```

  dev.IncrementBlockValue(block, 12);
```

####10.读数据块的value值<br>
**·ReadBlockValue(int block)**

|函数原型|public int[ ] ReadBlockValue(int block)|
|:----    |:---|
|功能描述 |读数据块的value值|
|参数说明 |block：数据块  |
|返回类型 |成功返回int[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.ReadBlockValue(block);
```

####11.写数据块的value值<br>
**·WriteBlockValue(int block, int value)**

|函数原型|public int WriteBlockValue(int block, int value)|
|:----    |:---|
|功能描述 |向一个数据块写入value值|
|参数说明 |block：数据块,value：向此block写入的value值  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
dev.WriteBlockValue(block, 66);
```

Ultralight卡
---
####1.设备上电<br>
**·InitDev()**

|函数原型|public int InitDev()|
|:----    |:---|
|功能描述 |设备上电|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  dev.InitDev();
```

####2.设备下电<br>
**·ReleaseDev()**

|函数原型|public void ReleaseDev()|
|:----    |:---|
|功能描述 |设备下电|
|参数说明 |无  |
|返回类型 |无|

 **示例**

```
  dev.ReleaseDev();
```

####3.非接卡寻卡<br>
**·SearchCard()**

|函数原型|public byte[ ] SearchCard()|
|:----    |:---|
|功能描述 |寻卡|
|参数说明 |无  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.SearchCard();
```

####4.读一个block数据块<br>
**·ReadBlock(int block)**

|函数原型|public byte[ ] ReadBlock(int block)|
|:----    |:---|
|功能描述 |读一个数据块|
|参数说明 |block：数据块  |
|返回类型 |成功返回byte[ ]，内容为读到的数据；失败返回null。|

 **示例**

```
  dev.ReadBlock(block);
```

####5.写一个block数据块<br>
**·WriteBlock(int block, byte[ ] data)**

|函数原型|public int WriteBlock(int block, byte[ ] data)|
|:----    |:---|
|功能描述 |写一个数据块|
|参数说明 |block：数据块，data：数据|
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```

 dev.WriteBlock(block, data);
```

####6.写一个block数据块（兼容模式）<br>
**·compatibility_Write_Block(int block, byte[ ] data)**

|函数原型|public int compatibility_Write_Block(int block, byte[ ] data)|
|:----    |:---|
|功能描述 |在兼容模式下写一个数据块|
|参数说明 |block:数据块，data：数据  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```

dev.compatibility_Write_Block(block, data1);
```

####7.移除卡片<br>
**·HaltCard()**

|函数原型|public int HaltCard()|
|:----    |:---|
|功能描述 |移除卡片，使其不能被寻找到|
|参数说明 |无  |
|返回类型 |0代表成功；非0代表失败。|

 **示例**

```
  dev.HaltCard();
```


北京思必拓科技股份有限公司
---

网址 http://www.speedata.cn/

技术支持联系方式：
电话：155 4266 8023
QQ：2480737278