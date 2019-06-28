リリースしなくてもレイアウトが試せる様な仕組みが欲しくなったので、AndroidでXMLからレイアウトを作るためのコンパイラが欲しいと思ったので作ってみる。
コンパイラを作ってみたGithubは[こちら](https://github.com/monar03/LayoutJSEngine)

# 何を作るか
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

# 実行
