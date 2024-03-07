package com.chen.data;

import lombok.Data;

/**
 * @Data 等于
 * @Getter
 * @Setter
 * @RequiredArgsConstructor  有参构造方法，入参为@NonNull修饰的属性
 * @ToString
 * @EqualsAndHashCode
 * @author: ChenJie
 * @date 2018/8/31
 */
@Data
public class UserData {
    private String name;
    private int age;
    private boolean male;
}
