/**
 * Created by Ramesses on 2018/12/17
 */

var app=angular.module(PROJECT);


/**
 * API认证请求
* */
app.factory('Auth', ['$window', '$http', '$q', function ($window, $http, $q) {
    var auth = {};
    var tokenKey = "new-user-authentication-token-key";

     auth.getUser = function () {
         if (!auth.isLoggedIn()) return null;
         var token = auth.getToken();
         if (token.indexOf('.') >= 0) 
         {
             token = token.split('.')[1];
         }
        return decode(token);
    };

    //检测用户是否已经登录
    auth.isLoggedIn = function () {
        var token = auth.getToken();
         if (!token) return false;
         if (token.indexOf('.') >= 0) {
            token = token.split('.')[1];
        }
        try {
            var payload = decode(token);
        } 
        catch (e) {
             return false;
        }
        var expire = new Date(payload.expire);
        var now = new Date(Date.now());
        return (expire >= now && payload.face);
    };

    auth.setToken = function (token) {
        $window.localStorage["tokenKey"] =token;
    };

    auth.getToken = function () {
        return $window.localStorage["tokenKey"];
    };

    //执行登录操作,向服务器发送请求，然后接受token保存到本地
    //返回一个promise
    auth.login = function (user) {
        var defer = $q.defer();

    //     $http.post(' http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Paper/', user)
    //     .success(function (data, status, headers) {
    //         log(data);
    //         log(headers);
    //         log(status);
    //         auth.setToken(data.token);
    //         log("login");
    //         log(auth.getToken());
    //         defer.resolve();


    //      }).error(function (data, status, headers) {
    //         console.log('error' + status);
    //         defer.reject(data);
    // });


    return defer.promise;
  };



  //退出登录状态，删除本地的token
  auth.logout = function () {
    $window.localStorage.removeItem(tokenKey);
  };

  return auth;
}]);

/**
 * 统一restful api请求
* */

app.factory('RESTClient', ['$http', '$q', function($http, $q){
    var self=

    {

        responseCode:0,

        getResource: function(url,data){
            var defer = $q.defer();
            var str='';
            for(var p in data)
            {
                str+=(p+'='+data[p]+"&");
            }
            if(str!='')
            {
                str.substring(0,str.length-1);
                str='?'+str;
            }
            $http.get(url+str)
            .then(function(res){
                defer.resolve(res.data);
            }, function(res){
                defer.reject(res.data);
            });
            return defer.promise;
        },


        addResource: function(url, data){
            var defer = $q.defer();
            $http.post(url,data)
                .then(function(res){
                    defer.resolve(res.data);
                }, function(res){
                    defer.reject(res.data);
                });
            return defer.promise;
        },

        deleteResource: function(url){
            var defer = $q.defer();
            $http.delete(url)
            .then(function(res){
                defer.resolve(res.data);
            }, function(res){
                defer.reject(res.data);
            });
            return defer.promise;
        },

        deleteDataResource: function(url,data){
            var defer = $q.defer();
            $http.delete(url,{
                data:data
            })
            .then(function(res){
                defer.resolve(res.data);
            }, function(res){
                defer.reject(res.data);
            });
            return defer.promise;
        },


        updateResource: function(url, data){
            var defer = $q.defer();
            $http.put(url, data, {
        headers: {'passwd':"Rmp1008."} 
        }).then(function(res){
                log(res);
                defer.resolve(res.data);
            }, function(res){
                defer.reject(res.data);
            });
            return defer.promise;
        }




    }

    return self;

}]);



app.factory('Category',function(RESTClient,URL){

    var self={};

    if(ENV.PROD)
    {
        RESTClient.getResource(URL.REQUEST+'User')
        .then( function(data)
        {
            self.users=data;
        });

        RESTClient.getResource(URL.REQUEST+'Option')
        .then( function(data)
        {
            self.option=data;
        });

         RESTClient.getResource(URL.REQUEST+'User')
        .then( function(data)
        {
            self.recUsers=data;
        });

        RESTClient.getResource(URL.REQUEST+'Card')
        .then( function(data)
        {
            self.cards=data;
        });

        RESTClient.getResource(URL.REQUEST+'User')
        .then( function(data)
        {
            self.record=data;
        });

        RESTClient.getResource(URL.REQUEST+'Seller')
        .then( function(data)
        {
            self.schedule=data;
        });
        RESTClient.getResource(URL.REQUEST+'Order')
        .then( function(data)
        {
            self.order=data;
        });
        RESTClient.getResource(URL.REQUEST+'Volunteer')
        .then( function(data)
        {
            self.volunteer=data;
        });
        RESTClient.getResource(URL.REQUEST+'Tag')
        .then( function(data)
        {
            self.tags=data;
        });
    }
    else
    {
        RESTClient.getResource(URL.DATA+'data'+URL.BACK)
        .then( function(data)
        {
            console.log("file");
            console.log(data);
            self.users=data.users;
            self.option=data.option;
            self.cards=data.cards;
            self.recUsers=data.recUsers;
            self.record=data.record;
            self.schedule=data.schedule;
            self.order=data.order;
            self.volunteer=data.volunteer;
            self.tags=data.tags;
        });

    }


/*-------------------------标签(tags)相关接口-----------------------------*/


    /*根据ID获取标签*/
    self.getTagById=function(_id)
    {
        for (var i=0;i<self.tags.length;i++)
        {
            if(self.tags[i].id==_id)
            {
                return self.tags[i];
            }
        }

    }

     /*根据名片获取标签*/
    self.getTagByCard=function(_id)
    {
        var tmp=[];
        for(var i=0;i<self.tags.length;i++)
        {
            if(self.tags[i].id==_id)
            tmp.push( self.tags[i]);
        }
        return tmp;

    }

     /*添加标签*/
    self.addTag=function(newTag)
    {

        self.tags.push(newTag);
    }

     /*根据ID修改标签*/
    self.setTagById=function(_id)
    {
        for (var i=0;i<self.tags.length;i++)
        {
            if(self.tags[i].id==_id)
            {
                if(self.tags[i].isVol==0)
                {
                    self.tags[i].isVol==1;
                }
                else
                {
                    self.tags[i].isVol==0;
                }
                return;
            }
        }
        return;

    }


/*-------------------------活动记录(record)相关接口-----------------------------*/
    
     /*根据用户获取记录*/
    self.getRecordByUser=function(_id)
    {
        var tmp=[];
        for(var i=0;i<self.record.length;i++)
        {
            if(self.record[i].id==_id)
            tmp.push( self.record[i]);
        }
        return tmp;

    }
     /*添加记录*/
    self.addRecord=function(newRec)
    {

        self.record.push(newRec);
    }

/*-------------------------活动(option)相关接口-----------------------------*/

     /*根据ID获取活动*/
    self.getOptById=function(_id)
    {
        for (var i=0;i<self.option.length;i++)
        {
            if(self.option[i].id==_id)
            {
                return self.option[i];
            }
        }
    }

     /*删除活动*/
     self.delOpt=function(index)
    {

        self.option.splice(index,1);
    }

    /*添加活动*/
    self.addOption=function(newOpt)
    {

        self.option.push(newOpt);
    }

     /*保存活动*/
    self.saveOption=function(index,newOpt)
    {

        self.option[index]=newOpt;
    }

    /*根据用户获取活动*/
    self.getOptByUser=function(_id)
    {
        var tmp=[];
        for (var i=0;i<self.option.length;i++)
        {
            if(self.option[i].id==_id)
            {
                tmp.push(self.option[i]);
            }
        }
        return tmp;
    }

/*-------------------------名片(cards)相关接口-----------------------------*/
     
     /*添加名片*/
    self.addCard=function(newCard)
    {

        self.cards.push(newCard);
    }

    /*保存名片*/
    self.saveCard=function(card)
    {

        for (var i=0;i<self.cards.length;i++)
        {
            if(self.cards[i].id==card.id)
            {
                self.cards[i]=card;
                return;
            }
        }

    }


    /*删除名片*/
    self.delCard=function(id)
    {     
         for (var i=0;i<self.cards.length;i++)
         {
            if(self.cards[i].id==id)
            {
                self.cards.splice(i,1);
                break;

            }
         }
         for (var i=0;i<self.users.length;i++)
         {
            for (var j=0;j<self.users[i].cards.length;j++)
            {
                if(self.users[i].cards[j].id==id)
                {
                    self.users[i].cards.splice(j,1); 
                }
            }
        
         }

        
    }


     /*根据ID获取名片*/
    self.getCardById=function(_id)
    {
        for (var i=0;i<self.cards.length;i++)
        {
            if(self.cards[i].id==_id)
            {
                return self.cards[i];
            }
        }
    }

/*-------------------------用户(users)相关接口-----------------------------*/

    /*添加用户*/
    self.addUser=function(newUser)
    {

        self.users.push(newUser);
    }

     /*保存用户*/
    self.saveUser=function(newUser)
    {
        for (var i=0;i<self.users.length;i++)
        {
            if(self.users[i].id==newUser.id)
            {
                self.users[i]=newUser;
                return;
            }
        }

    }

    /*根据ID获取用户*/
    self.getUserById=function(_id)
    {
        for (var i=0;i<self.users.length;i++)
        {
            if(self.users[i].id==_id)
            {
                return self.users[i];
            }
        }
    }

/*-------------------------日程(schedule)相关接口-----------------------------*/
    
    /*根据用户获取日程*/
    self.getSchByUser=function(_id)
    {
        var tmp=[];
        for(var i=0;i<self.schedule.length;i++)
        {
            if(self.schedule[i].id==_id)
            tmp.push( self.schedule[i]);
        }
        return tmp;

    }

     /*添加日程*/
    self.addSchedule=function(newSch)
    {

        self.schedule.push(newSch);
    }

    /*根据ID获取日程*/
    self.getSchById=function(_id)
    {

        var tmp=[];
        for (var i=0;i<self.schedule.length;i++)
        {
            if(self.schedule[i].id==_id)
            {
                tmp.push(self.schedule[i]);
            }
        }
        return tmp;
    }

/*-------------------------志愿者(volunteer)相关接口-----------------------------*/
   
    /*根据ID获取志愿者*/
    self.getVolById=function(_id)
    {

        for (var i=0;i<self.volunteer.length;i++)
        {
            if(self.volunteer[i].id==_id)
            {
                return self.volunteer[i];
            }
        }
        
    }

     /*根据ID修改志愿者信息*/
    self.setVolById=function(_id)
    {
        for (var i=0;i<self.users.length;i++)
        {
            if(self.users[i].id==_id)
            {
                if(self.users[i].isVol==0)
                {
                    self.users[i].isVol==1;
                }
                else
                {
                    self.users[i].isVol==0;
                }
                return;
            }
        }


    }

    /*删除志愿者*/
    self.delVol=function(index)
    {

        self.volunteer.splice(index,1);
    }



/*-------------------------订单(order)相关接口-----------------------------*/  

     /*根据ID获取订单*/
    self.getOrdById=function(_id)
    {
        var tmp=[];
        for (var i=0;i<self.order.length;i++)
        {
            if(self.order[i].id==_id)
            {
                tmp.push(self.order[i]);
            }
        }
        return tmp;
    }



  
    return self;
});

