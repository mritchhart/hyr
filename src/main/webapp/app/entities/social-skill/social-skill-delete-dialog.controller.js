(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('Social_skillDeleteController',Social_skillDeleteController);

    Social_skillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Social_skill'];

    function Social_skillDeleteController($uibModalInstance, entity, Social_skill) {
        var vm = this;
        vm.social_skill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Social_skill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
