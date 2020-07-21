def get_random_tags():
    tags = static_tags()['tags'][:]

    tags_to_return = []
    for index in range(0, len(tags)):
        tags_to_return.append({'tag': tags[index]['tag'], 'confidence': tags[index]['confidence']})

    return tags_to_return

def static_tags():
    return {"tags":[{"confidence":75.5640411376953,"tag":"patio"},{"confidence":59.8976135253906,"tag":"area"},{"confidence":50.0617027282715,"tag":"structure"},{"confidence":46.6889419555664,"tag":"architecture"},{"confidence":34.9993057250977,"tag":"building"},{"confidence":31.2862548828125,"tag":"sky"},{"confidence":30.7952919006348,"tag":"city"},{"confidence":28.1966552734375,"tag":"travel"},{"confidence":28.0813426971436,"tag":"tourism"},{"confidence":27.3244037628174,"tag":"tree"},{"confidence":24.4172763824463,"tag":"house"},{"confidence":20.7087383270264,"tag":"water"},{"confidence":20.2257213592529,"tag":"old"},{"confidence":19.5003223419189,"tag":"town"},{"confidence":18.8024425506592,"tag":"history"},{"confidence":18.6162738800049,"tag":"landscape"},{"confidence":18.3694496154785,"tag":"home"},{"confidence":18.1061687469482,"tag":"garden"},{"confidence":18.027271270752,"tag":"summer"},{"confidence":17.4147262573242,"tag":"grass"},{"confidence":17.2803344726562,"tag":"stone"},{"confidence":17.048490524292,"tag":"religion"},{"confidence":16.5401668548584,"tag":"palace"},{"confidence":16.5244369506836,"tag":"residence"},{"confidence":15.683765411377,"tag":"exterior"},{"confidence":14.7469654083252,"tag":"vacation"},{"confidence":14.7182598114014,"tag":"ancient"},{"confidence":14.5453319549561,"tag":"culture"},{"confidence":14.4672164916992,"tag":"roof"},{"confidence":14.3805856704712,"tag":"residential"},{"confidence":14.2651100158691,"tag":"estate"},{"confidence":13.8585042953491,"tag":"villa"},{"confidence":13.8213415145874,"tag":"street"},{"confidence":13.7074499130249,"tag":"construction"},{"confidence":13.1915283203125,"tag":"park"},{"confidence":13.1222629547119,"tag":"urban"},{"confidence":12.8788204193115,"tag":"luxury"},{"confidence":12.8294620513916,"tag":"tourist"},{"confidence":12.6553497314453,"tag":"landmark"},{"confidence":12.5070009231567,"tag":"village"},{"confidence":12.4873723983765,"tag":"traditional"},{"confidence":12.3016748428345,"tag":"buildings"},{"confidence":12.2722911834717,"tag":"temple"},{"confidence":12.1980104446411,"tag":"holiday"},{"confidence":11.8186883926392,"tag":"brick"},{"confidence":11.575083732605,"tag":"trees"},{"confidence":11.2238063812256,"tag":"wall"},{"confidence":11.207103729248,"tag":"sunny"},{"confidence":10.7144222259521,"tag":"outdoor"},{"confidence":10.5077772140503,"tag":"fountain"},{"confidence":10.4524612426758,"tag":"real"},{"confidence":10.2497262954712,"tag":"resort hotel"},{"confidence":10.1839647293091,"tag":"church"},{"confidence":10.0958280563354,"tag":"historic"},{"confidence":9.98354530334473,"tag":"university"},{"confidence":9.96910381317139,"tag":"maze"},{"confidence":9.94687938690186,"tag":"road"},{"confidence":9.85692501068115,"tag":"tower"},{"confidence":9.52115058898926,"tag":"statue"},{"confidence":9.33069801330566,"tag":"castle"},{"confidence":9.28302669525146,"tag":"plants"},{"confidence":9.23789405822754,"tag":"flower"},{"confidence":8.6436243057251,"tag":"spring"},{"confidence":8.5869607925415,"tag":"panorama"},{"confidence":8.4947681427002,"tag":"lawn"},{"confidence":8.47606754302979,"tag":"historical"},{"confidence":8.42812252044678,"tag":"destination"},{"confidence":8.32069969177246,"tag":"cathedral"},{"confidence":8.08988285064697,"tag":"coast"},{"confidence":7.90333604812622,"tag":"resort"},{"confidence":7.88993310928345,"tag":"landscaping"},{"confidence":7.7577109336853,"tag":"cloud"},{"confidence":7.6006031036377,"tag":"center"},{"confidence":7.45293045043945,"tag":"famous"},{"confidence":7.33175563812256,"tag":"island"},{"confidence":7.29612112045288,"tag":"new"},{"confidence":7.27654123306274,"tag":"dwelling"},{"confidence":7.12622261047363,"tag":"river"},{"confidence":7.03374290466309,"tag":"intersection"}]}