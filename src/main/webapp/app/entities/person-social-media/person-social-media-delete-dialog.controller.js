(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialMediaDeleteController',PersonSocialMediaDeleteController);

    PersonSocialMediaDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonSocialMedia'];

    function PersonSocialMediaDeleteController($uibModalInstance, entity, PersonSocialMedia) {
        var vm = this;

        vm.personSocialMedia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonSocialMedia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
