(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Teacher'];

    function HomeController ($scope, Principal, LoginService, $state, Teacher) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                $scope.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                $scope.isAuthenticated = vm.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        $scope.getTeacherInfo = function() {
            Teacher.query(function(result) {
               $scope.teachers = result;
               console.log(result);
               $scope.teacherInfo(result);
            });
        };
        $scope.getTeacherInfo();

        $scope.teacherInfo = function(teachers) {
            for (var i=0;i<teachers.length;i++){
            console.log(teachers[i]);
            console.log(teachers[i].email);
            console.log(vm.account.email);
                if (teachers[i].email.toLowerCase() == vm.account.email.toLowerCase()){
                    $scope.currentClassID =  teachers[i].classroom.id;
                    $scope.currentClassName = teachers[i].classroom.name;
                    $scope.teacherName = teachers[i].first_name + " " + teachers[i].last_name;
                    $scope.teacherNote = teachers[i].notes;
                    console.log("The current teacher is: " + $scope.teacherName);
                }
            }
        };

        /*$scope.verifyTeachPin = function(pinInput){

            if(pinInput == $scope.teacherNote){
                $scope.teacherPinConfirm = true;
                console.log("The current pin is: " + pinInput);
             }

             else {
             $scope.teacherPinFail = true;

             console.log( "INVALID PIN: " + pinInput );
             }
         }*/

    }
})();
