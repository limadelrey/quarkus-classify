import requests

#api_key = 'acc_33afef33b3732a2'
#api_secret = '0a723767233c0586613ef549c5ed2882'
#authorization = 'Basic YWNjXzMzYWZlZjMzYjM3MzJhMjowYTcyMzc2NzIzM2MwNTg2NjEzZWY1NDljNWVkMjg4Mg=='

def tags(api_key, api_secret, authorization, image_url):
    response = requests.get(
        'https://api.imagga.com/v2/tags?image_url=%s' % image_url,
        auth=(api_key, api_secret)
    )
    return response.json()
