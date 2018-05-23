package com.yuanxin.clan.core.util;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/24 0024 13:59
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

  public class Employee {
  //接口属性，方便后边注册
              JieKou jiekou;
  //注册一个接口属性，等需要调用的时候传入一个接口类型的参数，即本例中的Boss和Employee，
          public Employee zhuce(JieKou jiekou,Employee e){
             this.jiekou=jiekou;
             return e;
         }
 public void dosomething(){
//             System.out.println("拼命做事，做完告诉老板");
            //接口回调，如果没有注册调用，接口中的抽象方法也不会影响dosomething
            jiekou.show();
         }

         }
