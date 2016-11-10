# Mobile App API

### Database content

*users table: contains the users informations*
```sql
CREATE TABLE users (
id integer key autoincrement,
username text,
password text,
email text)
```
*users_settings table: contains the users settings*
```sql
CREATE TABLE users_settings(
user_id integer,
user_settings text,
foreign key(user_id) references users(id))
```

### API functionality

* Create new user
*Insert new user in database with username, [sha1]password and email*

```
 POST /api/register
 
 {"username":"toto", "password":"0b9c2625dc21ef05f6ad4ddf47c5f203837aa32c", "email":"toto@toto.com"}
```
Server response if successful registration
```
{"result": true}
```

* Generate token for user
*Generate a secure token for user, it will be used to process API actions*

```
 POST /api/generate-token

 {"username":"toto","password":"0b9c2625dc21ef05f6ad4ddf47c5f203837aa32c"}
```
Server response if credentials are corrects
```
{"token": "e6439969ca355b3c45362e7c37828240d688e7ee", "result": true}
```