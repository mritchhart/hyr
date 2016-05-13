(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('ClassroomDetailController', ClassroomDetailController);

    ClassroomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Classroom', 'Teacher', 'Student'];

    function ClassroomDetailController($scope, $rootScope, $stateParams, entity, Classroom, Teacher, Student) {
        var vm = this;
        vm.classroom = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:classroomUpdate', function(event, result) {
            vm.classroom = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
