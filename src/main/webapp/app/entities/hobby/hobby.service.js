(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Hobby', Hobby);

    Hobby.$inject = ['$resource'];

    function Hobby ($resource) {
        var resourceUrl =  'api/hobbies/:id';
        var findByPersonUrl =  'api/hobbies/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
