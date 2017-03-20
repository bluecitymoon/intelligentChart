(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkTexiActivityDeleteController',PersonNetworkTexiActivityDeleteController);

    PersonNetworkTexiActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonNetworkTexiActivity'];

    function PersonNetworkTexiActivityDeleteController($uibModalInstance, entity, PersonNetworkTexiActivity) {
        var vm = this;

        vm.personNetworkTexiActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonNetworkTexiActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
