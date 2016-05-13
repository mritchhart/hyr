(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Student_social_skillDeleteController',Student_social_skillDeleteController);

    Student_social_skillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Student_social_skill'];

    function Student_social_skillDeleteController($uibModalInstance, entity, Student_social_skill) {
        var vm = this;
        vm.student_social_skill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Student_social_skill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
