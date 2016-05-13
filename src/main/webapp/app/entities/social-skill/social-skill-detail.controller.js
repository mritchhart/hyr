(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Social_skillDetailController', Social_skillDetailController);

    Social_skillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Social_skill', 'Point_entry', 'Student_social_skill'];

    function Social_skillDetailController($scope, $rootScope, $stateParams, entity, Social_skill, Point_entry, Student_social_skill) {
        var vm = this;
        vm.social_skill = entity;
        
        var unsubscribe = $rootScope.$on('hopeRanchLearningAcademyApp:social_skillUpdate', function(event, result) {
            vm.social_skill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
