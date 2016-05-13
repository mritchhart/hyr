(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Point_entryDeleteController',Point_entryDeleteController);

    Point_entryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Point_entry'];

    function Point_entryDeleteController($uibModalInstance, entity, Point_entry) {
        var vm = this;
        vm.point_entry = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Point_entry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
