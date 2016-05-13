(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('ClassroomDeleteController',ClassroomDeleteController);

    ClassroomDeleteController.$inject = ['$uibModalInstance', 'entity', 'Classroom'];

    function ClassroomDeleteController($uibModalInstance, entity, Classroom) {
        var vm = this;
        vm.classroom = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Classroom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
