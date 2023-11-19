![league maker](https://github.com/RatkoSebez/LeagueMaker/blob/main/frontend/src/assets/lgmcover.jpg "LGM logo")

![GitHub Workflow Status (with event)](https://img.shields.io/github/actions/workflow/status/RatkoSebez/LeagueMaker/.github%2Fworkflows%2Fbuild-backend.yml)
![GitHub Workflow Status (with event)](https://img.shields.io/github/actions/workflow/status/RatkoSebez/LeagueMaker/.github%2Fworkflows%2Ftest-backend.yml?label=tests)
[![codecov](https://codecov.io/github/RatkoSebez/LeagueMaker/graph/badge.svg?token=4iLJk3VdQR)](https://codecov.io/github/RatkoSebez/LeagueMaker)

## About

League Maker is a web application designed to help users in both creating and managing competitions. Its user-friendly interface simplifies the setup and management of competitions, making the process easy and efficient. Currently there are 2 types of competitions available.

1. Round Robin League
2. Single Elimination Tournament

Below are screenshots displaying several pages from the website.
1. Home page
![league maker](https://github.com/RatkoSebez/LeagueMaker/blob/main/frontend/src/assets/homepage.png "Home page")
2. Tournament page
![league maker](https://github.com/RatkoSebez/LeagueMaker/blob/main/frontend/src/assets/tournament.png "Tournament page")
3. League page
![league maker](https://github.com/RatkoSebez/LeagueMaker/blob/main/frontend/src/assets/league.png "League page")
4. User profile page
5. ![league maker](https://github.com/RatkoSebez/LeagueMaker/blob/main/frontend/src/assets/profile.png "Profile page")

## Usage

1. Docker setup  
position in the root folder of the project where docker-compose.yml file is located  
run ``` docker-compose up ```  
open localhost:80 in your browser  

2. Local setup  
Visit [tech stack](#tech-stack) for more information about the technologies used in the project.

3. Default username and password: user and 123456789

## Tech stack

Frontend:
- angular 14
- node 16
- bootstrap 5

Backend:
- spring boot 2.6.1
- postgresql 14
