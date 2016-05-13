(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Student', 'Classroom', 'Student_social_skill', 'Point_entry'];

    function StudentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Student, Classroom, Student_social_skill, Point_entry) {
        var vm = this;
        vm.student = entity;
        vm.classrooms = Classroom.query();
        vm.student_social_skills = Student_social_skill.query();
        vm.point_entries = Point_entry.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeRanchLearningAcademyApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setPhoto = function ($file, student) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        student.photo = base64Data;
                        student.photoContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
