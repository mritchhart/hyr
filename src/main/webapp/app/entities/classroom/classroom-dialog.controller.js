(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('ClassroomDialogController', ClassroomDialogController);

    ClassroomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Classroom', 'Teacher', 'Student'];

    function ClassroomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Classroom, Teacher, Student) {
        var vm = this;
        vm.classroom = entity;
        vm.teachers = Teacher.query();
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:classroomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.classroom.id !== null) {
                Classroom.update(vm.classroom, onSaveSuccess, onSaveError);
            } else {
                Classroom.save(vm.classroom, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
