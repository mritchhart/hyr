(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('TeacherDetailController', TeacherDetailController);

    TeacherDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Teacher', 'Classroom', 'Point_entry'];

    function TeacherDetailController($scope, $rootScope, $stateParams, entity, Teacher, Classroom, Point_entry) {
        var vm = this;
        vm.teacher = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:teacherUpdate', function(event, result) {
            vm.teacher = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
