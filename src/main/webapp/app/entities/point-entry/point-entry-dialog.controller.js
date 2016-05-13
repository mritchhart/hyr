(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Point_entryDialogController', Point_entryDialogController);

    Point_entryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Point_entry', 'Teacher', 'Student', 'Social_skill'];

    function Point_entryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Point_entry, Teacher, Student, Social_skill) {
        var vm = this;
        vm.point_entry = entity;
        vm.teachers = Teacher.query();
        vm.students = Student.query();
        vm.social_skills = Social_skill.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:point_entryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.point_entry.id !== null) {
                Point_entry.update(vm.point_entry, onSaveSuccess, onSaveError);
            } else {
                Point_entry.save(vm.point_entry, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.ent_submission_time = false;
        vm.datePickerOpenStatus.ent_action_time = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
