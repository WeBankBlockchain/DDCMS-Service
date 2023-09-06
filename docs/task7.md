# Task7 添加数据上链的多样性

> 作者：李奇龙
>
> 学校：广东工业大学

## 一、添加内容

1. 添加枚举类`DataSchemaType`：用于描述数据目录类型

   ```java
   package com.webank.ddcms.enums;
   
   import lombok.AllArgsConstructor;
   import lombok.Getter;
   import lombok.extern.slf4j.Slf4j;
   
   /**
    * 描述数据目录可见状态的枚举类
    * @author lql
    * @date 2023/09/04
    */
   @AllArgsConstructor
   @Getter
   @Slf4j
   public enum DataSchemaType {
       ONLY_SELF(0),
       VISIBLE(1);
   
       private final int code;
   
       public static DataSchemaType getDataType(int code) {
           for (DataSchemaType type : DataSchemaType.values()) {
               if (type.code == code) {
                   return type;
               }
           }
           return null;
       }
   }
   ```

2. 更新`DataSchemaContract`合约对应的java类

3. `DataSchemaServiceImpl`类中添加代码片段：行数327-352

   > 作用：调用合约中的`createDataDetail`函数

   ```java
   	TransactionReceipt dataDetailReceipt = null;
       // determine visibility
       if (schemaRequest.getVisible().equals(DataSchemaType.VISIBLE.getCode())) {
         dataDetailReceipt = dataSchemaModule.createDataDetail(
                 Bytes32.DEFAULT.getValue(),
                 HexUtil.decodeHex(product.getProductBid()),
                 dataSchemaModule.getCreateDataSchemaOutput(receipt).getValue1(),
                 String.valueOf(schemaRequest.getProductId()),
                 schemaRequest.getContentSchema(),
                 schemaRequest.getDataSchemaName());
       } else {
         // encode
         byte[] key = new Keccak256().hash(bo.getEntity().getPrivateKey().getBytes(StandardCharsets.UTF_8));
         SymmetricCrypto ase = new SymmetricCrypto(SymmetricAlgorithm.AES,key);
         String encodeContent = ase.encryptHex(schemaRequest.getContentSchema());
         String encodeName = ase.encryptHex(schemaRequest.getDataSchemaName());
   
         dataDetailReceipt = dataSchemaModule.createDataDetail(
                 hash,
                 HexUtil.decodeHex(product.getProductBid()),
                 dataSchemaModule.getCreateDataSchemaOutput(receipt).getValue1(),
                 String.valueOf(schemaRequest.getProductId()),
                 encodeContent,
                 encodeName);
       }
       BlockchainUtils.ensureTransactionSuccess(dataDetailReceipt, txDecoder);
   ```

   > 说明：根据用户决定的数据可见性进行操作
   >
   > * 若是**对所有人可见**，则直接将数据传入合约
   > * 若**仅对自己可见**，则采用AES对称加密算法，首先使用当前用户的私钥生成对应AES算法的key，然后加密数据后传入合约

   ## 二、测试结果

   测试1截图如下：

   ![微信图片_20230906150720.png](https://x.imgs.ovh/x/2023/09/06/64f8277bb0511.png)

   ---

   测试2截图如下：

   ![c91374c32fea50aaa21c9b732e6cb08.png](https://x.imgs.ovh/x/2023/09/06/64f827a950ba7.png)