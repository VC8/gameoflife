# base image
FROM node:10

# set working directory
RUN mkdir /usr/src/app
WORKDIR /usr/src/app

# add `/usr/src/app/node_modules/.bin` to $PATH
ENV PATH /usr/src/app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY build /usr/src/app/build
RUN npm install -g serve --silent

# expose port
EXPOSE 3000

# start app
ENTRYPOINT ["serve", "build"]
