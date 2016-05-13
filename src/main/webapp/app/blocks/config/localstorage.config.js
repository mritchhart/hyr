(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .config(localStorageConfig);

    localStorageConfig.$inject = ['$localStorageProvider'];

    function localStorageConfig($localStorageProvider) {
        $localStorageProvider.setKeyPrefix('jhi-');
    }
})();
