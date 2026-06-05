# 🍳 たまログ

> たまご料理に特化したレシピ共有Webアプリケーション

---

## 📖 概要

「たまログ」は、たまご料理のレシピを投稿・共有できるWebアプリです。
ユーザー登録・ログインを行い、自分だけのレシピを投稿したり、他のユーザーのレシピをお気に入り登録することができます。
初めて開発した、webアプリ・チーム開発アプリです。
私は設計と役割分担などを行いましたが、初のチーム開発でうまくできなかった印象があります。

【次回課題】

・初期の設計から時間をかけて行う

・役割分担の際はタスクを細かく切り分け、管理表の作成を行う

・AI前提のチームワークは最初にフィールド名などを統一しておく必要があった


---

## ✨ 主な機能

| 機能 | 説明 |
|------|------|
| ユーザー登録・ログイン | Spring Securityによる認証 |
| レシピ投稿 | タイトル・画像・卵の数・材料・作り方・カテゴリーを登録 |
| レシピ一覧・検索 | キーワード・カテゴリーで絞り込み |
| レシピ詳細 | ステップ形式の作り方表示・卵アニメーション |
| レシピ編集・削除 | 投稿者のみ操作可能 |
| お気に入り | レシピをお気に入り登録・解除・一覧表示 |
| ランキング | アクセス数順にレシピを表示 |
| 画像アップロード | JPG・PNG・WebP対応 |

---

## 🛠 技術スタック

| 分類 | 技術 |
|------|------|
| バックエンド | Java 21 / Spring Boot 3.5 |
| セキュリティ | Spring Security |
| ORM | MyBatis |
| テンプレートエンジン | Thymeleaf |
| データベース | PostgreSQL |
| ビルドツール | Gradle |
| IDE | Eclipse (Pleiades) |

---

## 🗂 プロジェクト構成

```
src/main/java/com/example/demo/
├── controller/
│   ├── MainController.java
│   ├── FavoritesController.java
│   ├── LoginController.java
│   └── SignupController.java
├── service/
│   ├── RecipesService.java / RecipesServiceImpl.java
│   ├── FavoritesService.java / FavoritesServiceImpl.java
│   ├── CustomUserDetails.java
│   └── UserDetailsServiceImpl.java
├── mapper/
│   ├── RecipesMapper.java
│   ├── FavoritesMapper.java
│   └── UsersRepository.java
├── entity/
│   ├── Recipes.java
│   ├── Users.java
│   └── Favorites.java
├── form/
│   ├── RecipesForm.java
│   └── UsersForm.java
└── security/
    └── SecurityConfig.java

src/main/resources/
├── mapper/
│   ├── RecipesMapper.xml
│   ├── FavoritesMapper.xml
│   └── UsersMapper.xml
├── templates/
│   ├── login.html
│   ├── signup.html
│   ├── main.html
│   └── main/
│       ├── list.html
│       ├── register.html
│       ├── detail.html
│       ├── edit.html
│       ├── ranking.html
│       └── favorites.html
├── static/images/
├── schema.sql
└── application.properties
```

---

## 🗄 データベース設計

### users テーブル
| カラム | 型 | 説明 |
|--------|-----|------|
| id | SERIAL PK | ユーザーID |
| username | VARCHAR(50) | ユーザー名 |
| password | VARCHAR(255) | パスワード |
| created_at | TIMESTAMP | 作成日時 |

### recipes テーブル
| カラム | 型 | 説明 |
|--------|-----|------|
| id | SERIAL PK | レシピID |
| user_id | INTEGER FK | 投稿者ID |
| title | VARCHAR(200) | タイトル |
| eggs | INTEGER | 卵の数 |
| description | TEXT | 作り方 |
| ingredients | TEXT | 材料 |
| image_url | VARCHAR(500) | 画像パス |
| access_count | INTEGER | アクセス数 |
| category | VARCHAR(50) | カテゴリー |
| created_at | TIMESTAMP | 作成日時 |
| updated_at | TIMESTAMP | 更新日時 |

### favorites テーブル
| カラム | 型 | 説明 |
|--------|-----|------|
| id | SERIAL PK | お気に入りID |
| user_id | INTEGER FK | ユーザーID |
| recipe_id | INTEGER FK | レシピID |
| created_at | TIMESTAMP | 作成日時 |

---

## ⚙️ セットアップ

### 前提条件
- Java 21
- PostgreSQL
- Eclipse (Pleiades 2025)

### 手順

1. リポジトリをクローン
```bash
git clone https://github.com/yourname/tamagoclub.git
```

2. PostgreSQLでデータベースを作成
```sql
CREATE DATABASE springdb;
```

3. `application.properties` のDB接続情報を設定
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springdb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

4. `src/main/resources/static/images/` フォルダを作成

5. **Eclipseへのインポート（重要）**
    - `ファイル` → `インポート`
    - `Gradle` → `既存のGradleプロジェクト` を選択
    - `build.gradle` があるフォルダを指定
    - `完了`
    - インポート後にライブラリが消えた場合はプロジェクトを右クリック → `Gradle` → `Gradleプロジェクトのリフレッシュ`

6. ブラウザで `http://localhost:8080` にアクセス


---

## 📝 備考

- 画像は `src/main/resources/static/images/` に保存されます
- パスワードはハッシュ化せず `{noop}` プレフィックスで保存しています（学習用）
