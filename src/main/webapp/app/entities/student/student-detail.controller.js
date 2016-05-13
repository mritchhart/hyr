(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Student', 'Classroom', 'Student_social_skill', 'Point_entry'];

    function StudentDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Student, Classroom, Student_social_skill, Point_entry) {
        var vm = this;
        vm.student = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
