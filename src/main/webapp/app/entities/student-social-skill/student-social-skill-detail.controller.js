(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Student_social_skillDetailController', Student_social_skillDetailController);

    Student_social_skillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Student_social_skill', 'Social_skill', 'Student'];

    function Student_social_skillDetailController($scope, $rootScope, $stateParams, entity, Student_social_skill, Social_skill, Student) {
        var vm = this;
        vm.student_social_skill = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:student_social_skillUpdate', function(event, result) {
            vm.student_social_skill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
