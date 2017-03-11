(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialHotDiscussDeleteController',PersonSocialHotDiscussDeleteController);

    PersonSocialHotDiscussDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonSocialHotDiscuss'];

    function PersonSocialHotDiscussDeleteController($uibModalInstance, entity, PersonSocialHotDiscuss) {
        var vm = this;

        vm.personSocialHotDiscuss = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonSocialHotDiscuss.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
