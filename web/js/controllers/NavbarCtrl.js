/**
 * Created by Ramesses on 2018/12/17
 */


var app = angular.module(PROJECT);


app.controller('NavbarCtrl',function($scope,$rootScope,$stateParams,$state)
{

    $rootScope.curMode=0;
    $rootScope.addState=1;
    $rootScope.pageName=home;
    $rootScope.proId=null;
    $rootScope.epiId=null;
    $rootScope.help=null;
    $scope.userSt=0;

    $scope.back=function()
    {

       log(" click back"); 
       log($rootScope.pageName);
      if($rootScope.pageName=='brief')
      {
         $state.go('user',{programId:$rootScope.proId});  
      }
      else if($rootScope.pageName=='category')
      {
         $state.go('home');  
      }
      else
      {
          $state.go('home');  
      }

    }



    $scope.manage=function()
    {
      $state.go('manage');  
    }

   
    //log($state.current);
});