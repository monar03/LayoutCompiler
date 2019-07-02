# 使い方
gradleの設定

```
repositories {
    maven { url "https://monar03.github.io/mvn-repo"}
}

dependencies {
    implementation "jp.aquagear.layout.compiler:LayoutCompiler:1.1"
}
```

呼び出し方

```

Compiler compiler = Compiler(<クラスマップ>);
String designStr = ".test {
    padding:10px;
}";

String layoutStr = "<view><text class="test">aaa</text><view>";
List<Render> renders = compiler.compile("レイアウト文字列", "デザイン");
```

リリースしなくてもレイアウトが試せる様な仕組みが欲しくなったので、AndroidでXMLからレイアウトを作るためのコンパイラが欲しいと思ったので作ってみる。
コンパイラを作ってみた

XMLとJSからAndroidで画面を表示できる様にする。

```
入力するXML
<view>
    <text>
        {{test1}}
    </text>
    <text>
        {{test2}}
    </text>
</view>
```

# Compilerの処理
作るコンパイラの処理は、この３つ
* 字句解析器を作る（Lexer）
* 構文解析器を作る
* 構文解析器を実行する

# 字句解析器
コードは[こちら](https://github.com/monar03/LayoutCompiler/blob/master/src/jp/aquagear/layout/compiler/render/lexer/Lexer.java)

```
入力するXML
<view>
    <text>
        {{test1}}
    </text>
    <text>
        {{test2}}
    </text>
</view>
```
↓これをタグの意味ごとにオブジェクトに変換

```
(view TagStart)
(text TagStart)
(String)
(text TagEnd)
(text TagStart)
(String)
(text TagEnd)
(text TagEnd)
```

```
<text />
タグ自体が閉じている場合についても
(text TagStart)
(text TagEnd)
という風に<text></text>と同じ処理にする
```

# 構文解析器を作る
字句解析結果を使って解析ツリーを作る
![image.png](https://qiita-image-store.s3.ap-northeast-1.amazonaws.com/0/449882/1fa16568-0901-db00-43ae-d8e4c36475d5.png)

# 実行
