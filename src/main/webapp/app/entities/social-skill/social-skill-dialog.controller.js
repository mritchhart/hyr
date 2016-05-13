(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Social_skillDialogController', Social_skillDialogController);

    Social_skillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Social_skill', 'Point_entry', 'Student_social_skill'];

    function Social_skillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Social_skill, Point_entry, Student_social_skill) {
        var vm = this;
        vm.social_skill = entity;
        vm.point_entries = Point_entry.query();
        vm.student_social_skills = Student_social_skill.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:social_skillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.social_skill.id !== null) {
                Social_skill.update(vm.social_skill, onSaveSuccess, onSaveError);
            } else {
                Social_skill.save(vm.social_skill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
