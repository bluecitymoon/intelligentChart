(function () {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonNetworkTexiActivity', PersonNetworkTexiActivity);

    PersonNetworkTexiActivity.$inject = ['$resource'];

    function PersonNetworkTexiActivity($resource) {
        var resourceUrl = 'api/person-network-texi-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'loadAllByPersonId': {
                method: 'GET',
                isArray: true,
                url: 'api/person-network-texi-activities/person/:id',
                params: {id: '@id', type: '@type'}
            }
        });
    }
})();
