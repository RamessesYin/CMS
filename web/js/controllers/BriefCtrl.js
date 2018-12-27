/**
 * Created by Ramesses on 2018/12/17
 */

var app = angular.module(PROJECT);

app.controller('BriefCtrl',function($scope,$rootScope,Category,URL,RESTClient,$timeout,$state,$stateParams,REQUEST,$rootScope)
{

    $scope.editProgramInfo=new Object();
    $scope.states=null;
    $scope.selected=[]; 
    var myDate = new Date();
    var programId=$stateParams.programId;
    var episodeId=$stateParams.episodeId;
    $rootScope.proId=programId;
    console.log(Category.cards);
    $scope.curCard=Category.getCardById(episodeId);
    $scope.tags=Category.getTagByCard(episodeId);
    var curUid=$scope.curCard.u_id;
    $scope.record=Category.getRecordByUser(curUid);
    $scope.option=Category.option;
    $scope.schedule=Category.getSchByUser(curUid);
    $scope.nRecord={};




    $scope.shareIMG="share.jpg"
    
 

    $scope.addTag=function()
    {

        $("#addTagModal").modal();
    }

    $scope.saveTag=function(newTag)
    {
        newTag.valid=1;
        newTag.id=episodeId
        $scope.tags.push(newTag);
        Category.addTag(newTag);
        //write back
        $("#addTagModal").modal('hide');
         prompt("添加标签成功，待审核");
    }

    $scope.addRecord=function()
    {
        $scope.nRecord.date=myDate.toLocaleDateString();
        $scope.nRecord.Location="上海";
        $("#addRecordModal").modal();
    }

    $scope.saveRecord=function(newRec)
    {
        newRec.id=curUid
        $scope.record.push(newRec);
        $("#addRecordModal").modal('hide');
        Category.addRecord(newRec);
        //write back
         prompt("添加记录成功");
    }

    $scope.editCard=function()
    {
        $scope.tmp=$scope.curCard;

        $("#editCardModal").modal();
    }

    $scope.saveCard=function(tmp)
    {
        $scope.curCard=tmp;
        Category.saveCard(tmp);
        $("#editCardModal").modal('hide');
        //write back
         prompt("修改卡片成功");
    }

    $scope.shareCard=function()
    {
        $("#shareModal").modal();
    }

    $scope.setSchedule=function()
    {
        $("#showSchModal").modal();
    }
    $scope.addSchedule=function()
    {
        $("#showSchModal").modal('hide');
        $("#addSchModal").modal();
    }
    $scope.saveSchedule=function(tmp)
    {
        tmp.id=curUid;
        tmp.partner=$scope.curCard.name;
        $scope.schedule.push(tmp);
        console.log($scope.schedule);
        Category.addSchedule(tmp);
        //write back
        $("#addSchModal").modal('hide');
        prompt("添加日程成功");
    }
    

    $scope.delCard=function()
    {
        //done the del
        Category.delCard(episodeId);
        prompt("删除卡片成功");
        $state.go('user',{programId:1});
    }
   
    $scope.toDetail=function(id)
    {
        window.open("index.html#detail/"+id);
    }




});