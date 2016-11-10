from flask import Blueprint, render_template, request, redirect, url_for
import utils
from json import dumps as jsonify



api = Blueprint('api', __name__)

@api.route('/api/generate-token', methods=['POST'])
def generate_token():
	"""Generate a secure token for the user"""
	user_info = request.get_json(force=True)
	if not 'username' in user_info or not 'password' in user_info:
		return render_template('json.html', content=jsonify({"result":"false", "msg":"'username' or/and 'password' is/are missing"}))
	if not utils.login(user_info):
		return render_template('json.html', content=jsonify({"result":"false"}))
	token = utils.random_token()
	db = utils.get_db()
	while utils.row_exists(db, 'users', 'token', token):
		token = utils.random_token()
	utils.update('users', 'username = ? and password = ?', ['token'], [token, user_info['username'], user_info['password']])
	return render_template('json.html', content=jsonify({"result":True, "token": token}))


@api.route('/api/register', methods=['POST'])
def user_register():
	user_info = request.get_json(force=True)
	if not 'username' in user_info or not 'password' in user_info or not 'email' in user_info:
		return render_template('json.html', content=jsonify({"result":False, "msg":"Missing parameters"}))
	return render_template('json.html', content=jsonify(utils.register(user_info)))

@api.route('/api/test')
def api_test():
	if func():
		return render_template('json.html', content='test')
	return render_template('json.html', content='test')