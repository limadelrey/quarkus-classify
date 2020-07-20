import site
import json
import random

def tags(image_url):
    with open('tags.json') as tags_json:
        data = json.load(tags_json)
        tags = data['result']['tags'][:]
        random.shuffle(tags)
        tags_to_return = []
        for index in range(6):
            tags_to_return.append({'tag': tags[index]['tag']['en'], 'confidence': tags[index]['confidence']})

        print(tags_to_return)
        return tags_to_return
