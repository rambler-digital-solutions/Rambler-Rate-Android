# Rambler-Rate-Android

## Usage

Initialize lib

```java
Configuration configuration = Configuration.newInstance(this)
                .setTheme(R.style.CustomRateTheme)
                .setDaysNotShow(0)
                .setTitle("Оцените приложение")
                .setMessage("Если вы кайфуете от нашего приложения, поставьте ему пять звездочек ;)");

RamblerRate.initialize(configuration);
```

Call for show dialog

```java
RamblerRate.startForResult(context);
```

Handle result

```java
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        RamblerRate.onActivityResult(requestCode, resultCode, data, new RamblerRate.Callback() {
            @Override
            public void rate(float stars) {
                
            }

            @Override
            public void remindLater() {
                
            }

            @Override
            public void cancel() {
                
            }
        });
    }
```

Stylize dialog

```xml
<style name="CustomRateTheme" parent="RateTheme">
  <item name="ratingHeaderStyle">@style/CustomRateTheme.Header</item>
  <item name="ratingHeaderContainerStyle">@style/HeaderContainer</item>
</style>

<style name="CustomRateTheme.Header" parent="RateTheme.Header">
  <item name="android:background">#f00</item>
</style>

<style name="HeaderContainer">
  <item name="android:visibility">gone</item>
</style>
```

Full list of attributes can be found [here](https://github.com/EdSergeev/Rambler-Rate-Android/blob/develop/rate/src/main/res/values/styles.xml)
