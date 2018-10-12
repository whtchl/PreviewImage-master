# Preview
一款支持全屏、分页、伸缩、自由配置加载图片引擎的图片预览工具

<p align="left">
   <a href="https://jitpack.io/#whamu2/PreviewImage">
    <img src="https://jitpack.io/v/whamu2/PreviewImage.svg" alt="Latest Stable Version" />
  </a>
  <a href="https://developer.android.com/about/versions/android-4.4.html">
    <img src="https://img.shields.io/badge/API-19%2B-blue.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
  <a href="https://github.com/whamu2">
    <img src="https://img.shields.io/badge/Author-whamu2-orange.svg?style=flat-square" alt="Author" />
  </a>
</p>

## Getting started

### Setting up the dependency

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```groovy
dependencies {
       implementation 'com.github.whamu2:PreviewImage:v1.1.0'
}
```

## 使用


```java
public class MainActivity extends AppCompatActivity {
    
    public void onClick(View v) {
        // 第一步：创建图片地址集合
        List<Image> images = new ArrayList<>();
        for (Data d : datas) {
            Image image = new Image();
            image.setOriginUrl(d.getOriginUrl()); // 高清原图，没有无需传
            image.setThumbnailUrl(d.getThumbnailUrl()); // 低分辨缩略图，必传
            images.add(image); 
        }
        
        Preview.with(MainActivity.this)
                  .builder()
                  .load(images) // 图片地址集合
                  .displayCount(true) // 是否展示顶部张数 如 1/12
                  .markPosition(0) // 标记从第几张开始显示
                  .showDownload(true) // 是否显示下载按钮，当未设置下载路径时不显示
                  .showOriginImage(true) // 是否显示原图，当没有原图时不会显示按钮
                  .downloadLocalPath("Preview") // 设置下载路径父文件夹，此路径为/storage/emulated/0/Pictures/Preview/
                  .show();
        }
}
```


