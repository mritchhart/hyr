(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Student_social_skillDialogController', Student_social_skillDialogController);

    Student_social_skillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Student_social_skill', 'Social_skill', 'Student'];

    function Student_social_skillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Student_social_skill, Social_skill, Student) {
        var vm = this;
        vm.student_social_skill = entity;
        vm.social_skills = Social_skill.query();
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:student_social_skillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.student_social_skill.id !== null) {
                Student_social_skill.update(vm.student_social_skill, onSaveSuccess, onSaveError);
            } else {
                Student_social_skill.save(vm.student_social_skill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.start_date = false;
        vm.datePickerOpenStatus.end_date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
