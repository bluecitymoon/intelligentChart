(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkShoppingDeleteController',PersonNetworkShoppingDeleteController);

    PersonNetworkShoppingDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonNetworkShopping'];

    function PersonNetworkShoppingDeleteController($uibModalInstance, entity, PersonNetworkShopping) {
        var vm = this;

        vm.personNetworkShopping = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonNetworkShopping.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
