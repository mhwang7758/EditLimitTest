# EditLimitTest介绍

很多情况下需要让用户只输入数字或者小数，因此都会在xml中设置其输入类型属性：`android:inputType="numberDecimal"`

但是光这样可不行，如果用户只输入“.”，那么由于这个“.”是字符串而不是数字，因此会引起程序崩溃。

这时可以对输入框的输入进行限制，不让用户输入不该输入的字符。本demo使用InputFilter对输入框进行限制，以达到想要的效果。

![测试图片](http://on2eediez.bkt.clouddn.com/test_editlimit.gif)

