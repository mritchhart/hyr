(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('TeacherDialogController', TeacherDialogController);

    TeacherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Teacher', 'Classroom', 'Point_entry'];

    function TeacherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Teacher, Classroom, Point_entry) {
        var vm = this;
        vm.teacher = entity;
        vm.classrooms = Classroom.query();
        vm.point_entries = Point_entry.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:teacherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.teacher.id !== null) {
                Teacher.update(vm.teacher, onSaveSuccess, onSaveError);
            } else {
                Teacher.save(vm.teacher, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
