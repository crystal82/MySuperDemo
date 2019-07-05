package com.knight.jone.mySuperDemo.easterEgg;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.util.Pools;

/**
 * 对象池尝试
 *
 * @author zone
 */
public class ObjectPools extends Activity {

    private Pools.Pool<TestBean> mPool = new Pools.SynchronizedPool<>(5);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (int i = 0; i < 5; i++) {
            mPool.release(new TestBean("name" + i, i));
        }

        for (int n = 0; n < 10; n++) {
            TestBean acquire = mPool.acquire();
            System.out.println(n + " ObjectPools:" + acquire);

            try {
                boolean release = mPool.release(acquire);
                System.out.println(n + "ObjectPools release:" + release);
            } catch (Exception e) {
                System.out.println(n + "ObjectPools Exception:" + e.toString());
            }
        }
    }

    class TestBean {
        String name;
        int age;

        public TestBean(String s, int i) {
            this.name = s;
            this.age = i;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
