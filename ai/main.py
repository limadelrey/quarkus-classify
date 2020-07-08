from flask import Flask, request, abort, make_response, jsonify
import requests

api_key = 'acc_33afef33b3732a2'
api_secret = '0a723767233c0586613ef549c5ed2882'
authorization = 'Basic YWNjXzMzYWZlZjMzYjM3MzJhMjowYTcyMzc2NzIzM2MwNTg2NjEzZWY1NDljNWVkMjg4Mg=='

app = Flask(__name__)

@app.route('/')
def index():
    return {
        'message': 'Hello from Quarkus Hackaton AI API'
    }

@app.route('/api/v1/tags')
def tags():
    image_url = request.args.get('image_url')
    if not image_url:
        abort(make_response(jsonify(error="Missing image_url query parameter"), 400))
    response = requests.get(
        'https://api.imagga.com/v2/tags?image_url=%s' % image_url,
        auth=(api_key, api_secret)
    )
    return response.json()
