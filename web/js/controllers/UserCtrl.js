/**
 * Created by Ramesses on 2018/12/17
 */
var app = angular.module(PROJECT);


app.controller('UserCtrl',function($scope,$rootScope,$http,RESTClient,$stateParams,Category,$state,URL)
{
    $rootScope.proId=$stateParams.programId;
    $scope.sourceUrl=URL.RESOURCE;
    $scope.users=Category.users;
    $scope.schedule=Category.schedule;
    $scope.tags=Category.tags;
    $scope.cards=Category.cards;
    $scope.recUsers=Category.recUsers;



    var curIndex=-1;
    $scope.userSt=1;

    /*添加卡片*/
    $scope.addCard = function (index)
    {
        curIndex=index;

        $("#CardModal").modal('show');
    }

    /*保存卡片*/
    $scope.saveCard = function (card)
    {
        card.id=$scope.cards.length+1;
        card.u_id=curIndex+1;
        $scope.cards.push(card);
        Category.addCard(card);
        console.log($scope.cards);
        $scope.users[curIndex].cards.push(card);
        Category.saveUser($scope.users[curIndex]);
        console.log($scope.users);
       $("#CardModal").modal('hide');
       prompt("添加成功");
    }

    /*查看推荐列表*/
    $scope.showRec=function ()
    {
        console.log("show rec");
        $("#friRecModal").modal(); 
    } 

    /*添加推荐好友*/
    $scope.addFri=function (index)
    {
        $scope.users[index].isVol=1;
        Category.users[index].isVol=1;
        prompt('添加成功');

    } 

    /*申请志愿者*/
     $scope.apply=function()
    {
        $scope.userSt=1; 
        $("#appVolModal").modal('hide'); 
        prompt("成功申请成为志愿者");
    }

    /*脱离志愿者*/
    $scope.exit=function()
    {
        $scope.userSt=0; 
        $("#exitVolModal").modal('hide');  
        prompt("成功取消担任志愿者");
    }

    /*志愿者设置*/
    $scope.setVol=function(idx)
    {
 
          if($scope.userSt==0)
          {
             $("#appVolModal").modal();
          }
          else if($scope.userSt==1)
          {
             $("#exitVolModal").modal();
          }


    }

    /*日程设置*/
     $scope.setSchedule=function()
    {
        $("#showSchModal").modal();
    }

    /*添加日程*/
    $scope.addSchedule=function()
    {
        $("#showSchModal").modal('hide');
        $("#addSchModal").modal();
    }

    /*保存日程*/
    $scope.saveSchedule=function(tmp)
    {

        var curUser=Category.getUserById(tmp.id);
        tmp.partner=curUser.name;
        console.log($scope.schedule);
        Category.addSchedule(tmp);
        console.log($scope.schedule);
        $("#addSchModal").modal('hide');
        prompt("添加日程成功");
    }

    /*通过标签*/
    $scope.pass=function(idx)
    {

         $scope.tags[idx].valid=0;

    }

    /*不通过标签*/
    $scope.reject=function(idx)
    {
         log("reject");
         $scope.tags[idx].valid=3;

    }

    /*跳转到卡片详情*/
    $scope.toBrief=function(id)
    {
        $state.go('brief',{programId:$stateParams.programId,episodeId:id});
    }


});
