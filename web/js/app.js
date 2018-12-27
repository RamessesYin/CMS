/**
 * Created by Ramesses on 2018/12/17
 */

'use strict';

//是否为生产环境
var ENV={
    PROD : 0
};

var PROJECT='edit';

var app = angular.module(PROJECT, ['ui.router']);



//定义有关url的常量
app.constant("URL", {
    "BASE"   : "/",
    "REQUEST": "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/",
    "FILE": "http://localhost/BPM/img/",
    "DATA":"http://localhost/BPM/data/",
    "BACK":".json"
});

//定义有关请求状态的常量
app.constant("REQUEST", {
    "UPDATE"   : {SUCCESS:'200'}
});



app.config(function($stateProvider, $urlRouterProvider){

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'partials/home.html',
            controller: 'HomeCtrl as home',
            onEnter: function($rootScope,URL){
                $rootScope.addState=1;
                $rootScope.pageName='home';
                $rootScope.FILE_URL=URL.FILE;
            }
        })
        .state('seller', {
            url: '/seller/:programId',
            templateUrl: 'partials/seller.html',
            controller: 'SellerCtrl as seller',
            onEnter: function($rootScope,URL){
                $rootScope.FILE_URL=URL.FILE;
                $rootScope.pageName='seller';
                $rootScope.addState=2;

            }
        })
        .state('brief', {
            url: '/brief/:programId/:episodeId',
            templateUrl: 'partials/brief.html',
            controller: 'BriefCtrl as brief',
            onEnter: function($rootScope,URL){
                $rootScope.FILE_URL=URL.FILE;
                $rootScope.pageName='brief';
                $rootScope.addState=2;

            }
        }) 
        .state('detail', {
            url: '/detail/:programId',
            templateUrl: 'partials/detail.html',
            controller: 'DetailCtrl as detail',
           onEnter: function($rootScope,URL){
                $rootScope.FILE_URL=URL.FILE;
                  $rootScope.pageName='detail';
                  $rootScope.addState=2;

            }
        })
        .state('manage', {
            url: '/manage/:programId',
            templateUrl: 'partials/manage.html',
            controller: 'ManageCtrl as manage',
           onEnter: function($rootScope,URL){
                $rootScope.FILE_URL=URL.FILE;
                $rootScope.pageName='manage';
                $rootScope.addState=2;

            }
        })

        .state('user', {
            url: '/user/:programId',
            templateUrl: 'partials/user.html',
            controller: 'UserCtrl as user',
            onEnter: function($rootScope,URL){
                $rootScope.FILE_URL=URL.FILE;
                $rootScope.pageName='user';
                $rootScope.addState=0;

            }
        });

});


app.run(['$state', function($state,$rootScope){
    //$rootScope.FILE_URL='http://202.120.39.165:2331';
    $state.go('home');
}]);


var DEBUG=true;

function log(msg)
{
    if(DEBUG)
    {
        console.log(msg);
    }
}

function prompt(p)
{
    alert(p);
}

function getRandom(min , max)
{
    var range=max-min;
    var rand=Math.random();
    return (min+Math.round(rand*range));
}


function sleep(n) {
    var start = new Date().getTime();
    log(start);
    while(true)  if(new Date().getTime()-start > n) break;
}



