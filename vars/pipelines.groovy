import groovy.transform.Field

@Field def configuration = [
    credentials: [
        git: 'git-credentials',
        docker: 'docker-credentials'
    ],
    hosts: [
        git: 'https://github.com',
        docker: 'https://hub.docker.com'
    ]
]

@Field def version = '1.2.3'