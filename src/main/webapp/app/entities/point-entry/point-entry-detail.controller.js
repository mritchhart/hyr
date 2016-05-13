(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Point_entryDetailController', Point_entryDetailController);

    Point_entryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Point_entry', 'Teacher', 'Student', 'Social_skill'];

    function Point_entryDetailController($scope, $rootScope, $stateParams, entity, Point_entry, Teacher, Student, Social_skill) {
        var vm = this;
        vm.point_entry = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:point_entryUpdate', function(event, result) {
            vm.point_entry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
